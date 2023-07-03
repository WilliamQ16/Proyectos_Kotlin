package com.example.calculadora_compose.ui.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.IllegalArgumentException
import java.util.EmptyStackException

class CalculatorViewModel: ViewModel() {

    private val _inputText = mutableStateOf(CalculatorState())

    val inputText: State<CalculatorState> = _inputText

    private val _outputText = mutableStateOf(CalculatorState())

    val outputText: State<CalculatorState> = _outputText

    fun onEvent(e: CalculatorEvent){
        when (e){
            CalculatorEvent.AllClear -> allClear()
            CalculatorEvent.BackSpace -> backSpace()
            CalculatorEvent.Calculate -> calculate()
            is CalculatorEvent.Write -> write(e.value)
        }
    }

    private fun allClear() {
        _inputText.value =  inputText.value.copy(
            text = ""
        )

        _outputText.value =  outputText.value.copy(
            text = ""
        )
    }

    private fun backSpace() {
        val length = _inputText.value.text.length

        if (length > 0){
            _inputText.value = inputText.value.copy(
                text = inputText.value.text.subSequence(0, length - 1) as String
            )
        }
    }

    private fun write(value: String) {
        if(_outputText.value.text == ""){
            _inputText.value = inputText.value.copy(
                text = inputText.value.text + value
            )
        } else {

            _inputText.value = inputText.value.copy(
                text = outputText.value.text
            )

            _outputText.value = outputText.value.copy(
                text = ""
            )

            _inputText.value = inputText.value.copy(
                text = _inputText.value.text + value
            )
        }
    }

    private fun calculate() {
        try {
            val expression = ExpressionBuilder(_inputText.value.text).build()
            val result = expression.evaluate()
            val longResult = result.toLong()
            if(result == longResult.toDouble()){
                _outputText.value = outputText.value.copy(
                    text = longResult.toString()
                )
            }else{
                _outputText.value = outputText.value.copy(
                    text = result.toString()
                )
            }
        } catch (e: IllegalArgumentException){
            _outputText.value = outputText.value.copy(
                text = ""
            )
        } catch (e: EmptyStackException){
            _outputText.value = outputText.value.copy(
                text = ""
            )
        }
    }

}