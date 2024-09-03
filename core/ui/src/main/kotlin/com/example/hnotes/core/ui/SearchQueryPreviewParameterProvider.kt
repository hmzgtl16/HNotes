package com.example.hnotes.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnotes.core.model.data.SearchQuery

class SearchQueryPreviewParameterProvider : PreviewParameterProvider<List<SearchQuery>> {

    override val values: Sequence<List<SearchQuery>> = sequenceOf(
        listOf(
            SearchQuery(query = "query01"),
            SearchQuery(query = "query02"),
            SearchQuery(query = "query03"),
            SearchQuery(query = "query04"),
            SearchQuery(query = "query05"),
            SearchQuery(query = "query06"),
            SearchQuery(query = "query07"),
            SearchQuery(query = "query08"),
            SearchQuery(query = "query09"),
            SearchQuery(query = "query10")
        )
    )
}