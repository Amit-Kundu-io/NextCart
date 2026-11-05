package com.amit_kundu_io.network.paginator


import com.amit_kundu_io.network.utilit.NetworkResource
import com.amit_kundu_io.network.utilit.NetworkUtils.API_ERROR
import kotlinx.coroutines.flow.Flow

/**
 * A lightweight, generic paginator for Kotlin Multiplatform (CMP).
 *
 * This class provides a reusable way to handle pagination without depending
 * on any platform-specific APIs (e.g., Android Paging).
 *
 * It does NOT enforce any specific result wrapper (like Result or sealed classes),
 * making it flexible and easy to integrate with any architecture.
 *
 * @param Key The type of the pagination key (e.g., Int for page, String for cursor).
 * @param Item The type of the response returned from the API.
 *
 * @param initialKey The starting key for pagination (e.g., 0 or 1).
 * @param request A suspend function that fetches data using the current key.
 *        Should throw exceptions on failure.
 * @param getNextKey Determines the next key based on the current key and result.
 * @param isEndReached Determines whether pagination should stop.
 *
 * Example usage:
 *
 * ```
 * val paginator = Paginator<Int, ProductResponse>(
 *     initialKey = 0,
 *     request = { page -> api.getProducts(page, 10) },
 *     getNextKey = { page, _ -> page + 1 },
 *     isEndReached = { page, response ->
 *         (page * 10) >= response.total
 *     }
 * )
 *
 * viewModelScope.launch {
 *     paginator.loadNext(
 *         onLoading = { loading -> /* update UI */ },
 *         onSuccess = { data -> /* append items */ },
 *         onError = { error -> /* show error */ }
 *     )
 * }
 * ```
 *
 * Features:
 * - Prevents duplicate requests
 * - Handles loading, success, and error states
 * - Supports any pagination strategy (page-based, offset-based, cursor-based)
 * - Fully compatible with Compose Multiplatform (CMP)
 *
 * Notes:
 * - Call [reset] to restart pagination (e.g., pull-to-refresh)
 * - Ensure [request] throws exceptions for proper error handling
 */

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

                    onError(result.message ?: API_ERROR)
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