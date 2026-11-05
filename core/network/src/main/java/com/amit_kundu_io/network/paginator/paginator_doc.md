#  Simple Generic Pagination for Kotlin Multiplatform 

## Why?

I wanted a pagination solution that:

*  Uses `Flow<NetworkResource<T>>`
*  Is reusable across multiple screens
* Keeps the ViewModel clean
* Doesn't depend on Android Paging 3

Instead of putting all pagination logic inside every ViewModel, I created a reusable `Paginator` class.

---

# Architecture

```text
UI (Compose)
      │
      ▼
ViewModel
      │
      ▼
Paginator
      │
      ▼
UseCase
      │
      ▼
Repository
      │
      ▼
API
```

The ViewModel only decides **when** to load the next page. The `Paginator` manages page numbers and prevents duplicate requests.

---

# Paginator

```kotlin
class Paginator<Key, Item>(
    private val initialKey: Key,
    private val request: (Key) -> Flow<NetworkResource<Item>>,
    private val getNextKey: (Key, Item) -> Key,
    private val isEndReached: (Key, Item) -> Boolean
) {

    private var currentKey: Key = initialKey
    private var isLoading: Boolean = false
    private var endReached: Boolean = false

    /**
     * Loads the next page of data.
     *
     * @param onLoading Called when loading state changes.
     * @param onSuccess Called when data is successfully fetched.
     * @param onError Called when an error occurs.
     */
    suspend fun loadNext(
        onLoading: (Boolean) -> Unit,
        onSuccess: (Item) -> Unit,
        onError: (String) -> Unit
    ) {

        if (isLoading || endReached) return

        request(currentKey).collect { result ->

            when (result) {

                is NetworkResource.Loading -> {

                    isLoading = true
                    onLoading(true)
                }

                is NetworkResource.Error -> {

                    isLoading = false
                    onLoading(false)

                    onError(result.message ?: "Unknown Error")
                }

                is NetworkResource.Success -> {

                    val response = result.data ?: return@collect

                    endReached = isEndReached(currentKey, response)

                    currentKey = getNextKey(currentKey, response)

                    isLoading = false
                    onLoading(false)
                    onSuccess(response)
                }
            }
        }
    }

    /**
     * Resets the paginator to its initial state.
     *
     * Useful for:
     * - Pull-to-refresh
     * - New search queries
     */
    fun reset() {
        currentKey = initialKey
        endReached = false
    }
}

```

# Create the Paginator

```kotlin
private val paginator = Paginator<Int, GetProjectsResponse>(

    initialKey = 1,

    request = { page ->
        getProjectUseCase(
            pageNo = page,
            pageSize = PAGE_SIZE
        )
    },

    getNextKey = { page, _ ->
        page + 1
    },

    isEndReached = { _, response ->
        response.data.projectList.size < PAGE_SIZE
    }
)
```

### Parameters

### `initialKey`

The first page to load.

```kotlin
initialKey = 1
```

---

### `request`

API request for the current page.

```kotlin
request = { page ->
    getProjectUseCase(
        pageNo = page,
        pageSize = PAGE_SIZE
    )
}
```

---

### `getNextKey`

Defines how to calculate the next page.

```kotlin
getNextKey = { page, _ ->
    page + 1
}
```

This can also support cursor-based pagination if required.

---

### `isEndReached`

Determines whether more data is available.

```kotlin
isEndReached = { _, response ->
    response.data.projectList.size < PAGE_SIZE
}
```

If the API returns fewer items than requested, pagination stops.

---

# ViewModel

Loading the next page becomes simple.

```kotlin
fun getProjects() {
    viewModelScope.launch {

        paginator.loadNext(

            onLoading = { isLoading ->

                val firstLoad = _state.value.projects.isEmpty()

                _state.update {
                    it.copy(
                        isLoading = firstLoad && isLoading,
                        isLoadingMore = !firstLoad && isLoading
                    )
                }
            },

            onSuccess = { response ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        projects = it.projects + response.data.projectList
                    )
                }
            },

            onError = { message ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = message
                    )
                }
            }
        )
    }
}
```

The ViewModel no longer needs to manage page numbers or detect when pagination should stop.

---

# Compose

Trigger loading when the user reaches the bottom of the list.

```kotlin
val lazyListState = rememberLazyListState()

LaunchedEffect(state.projects) {

    snapshotFlow {
        lazyListState.layoutInfo
            .visibleItemsInfo
            .lastOrNull()
            ?.index
    }
        .distinctUntilChanged()
        .collect { lastVisibleIndex ->

            if (lastVisibleIndex == state.projects.lastIndex) {
                onAction(ProjectsAction.OnNextPage)
            }
        }
}
```

---

# Benefits

* Generic and reusable
* Compatible with Kotlin Multiplatform
* Works with `Flow<NetworkResource<T>>`
* Keeps ViewModels cleaner
* Prevents duplicate pagination requests
* Easy to reuse for Projects, Employees, Vendors, or any paginated list

---

# Possible Improvements

Some ideas to make this approach even more robust:

* Refresh support
* Retry failed requests
* Cursor-based pagination
* Expose pagination state as `StateFlow`
* Cache with Room or SQLDelight
* Automatic prefetching before the last visible item
* Loading and error footer UI

---

# Feedback

This is the pagination approach I'm currently using in my Kotlin Multiplatform project.

I'm interested in hearing from other Android and KMP developers:

* Would you structure it differently?
* What improvements would you recommend?
* Have you built a reusable pagination component for KMP?

Constructive feedback is welcome!
