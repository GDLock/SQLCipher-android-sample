package com.example.sqlcipherexample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqlcipherexample.data.model.ColorState
import com.example.sqlcipherexample.data.dao.SampleDao
import com.example.sqlcipherexample.data.model.SampleEntity
import com.example.sqlcipherexample.domain.mapper.toComposeColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val dao: SampleDao
) : ViewModel() {

    private val list = ColorState.values().map { colorState ->
        colorState.name.lowercase().replaceFirstChar {
            it.titlecase(Locale.getDefault())
        }
    }

    val state = dao.get()
        .map { list ->
            list.map {
                SampleUiModel(
                    id = it.id,
                    name = it.name,
                    color = it.color.toComposeColor()
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun add() {
        val name = list.random()

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(
                SampleEntity(
                    name = name,
                    color = ColorState.valueOf(name.uppercase())
                )
            )
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
