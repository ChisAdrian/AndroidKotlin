package com.example.CalculatorInventory


import java.util.Stack

/*
    ChatGPT
    The final implementation I provided incorporates the
    Shunting Yard algorithm and handles operators, precedence, and parentheses appropriately.
    It splits the expression into tokens, identifies numeric values, operators, and parentheses,
    and then performs the evaluation step by step based on the operator precedence rules.
 */

class EvalMath  {

    private fun String.isNumeric(): Boolean {
        return try {
            this.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun String.isOperator(): Boolean {
        return this in setOf("+", "-", "*", "/")
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentToken = ""
        for (char in expression) {
            if (char.isDigit() || char == '.') {
                currentToken += char
            } else if (char.isWhitespace()) {
                if (currentToken.isNotEmpty()) {
                    tokens.add(currentToken)
                    currentToken = ""
                }
            } else {
                if (currentToken.isNotEmpty()) {
                    tokens.add(currentToken)
                    currentToken = ""
                }
                tokens.add(char.toString())
            }
        }
        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken)
        }
        return tokens
    }

    private fun hasPrecedence(operator1: String, operator2: String): Boolean {
        return (operator2 == "*" || operator2 == "/") && (operator1 == "+" || operator1 == "-")
    }

    private fun performOperation(operatorStack: Stack<String>, valueStack: Stack<Double>) {
        val operator = operatorStack.pop()
        if (valueStack.size < 2) {
            throw IllegalArgumentException("Invalid expression")
        }
        val operand2 = valueStack.pop()
        val operand1 = valueStack.pop()
        when (operator) {
            "+" -> valueStack.push(operand1 + operand2)
            "-" -> valueStack.push(operand1 - operand2)
            "*" -> valueStack.push(operand1 * operand2)
            "/" -> valueStack.push(operand1 / operand2)
        }
    }


    fun doCalculation(expression: String): Double {
        val tokens = tokenize(expression)

        val operatorStack = Stack<String>()
        val valueStack = Stack<Double>()

        for (token in tokens) {
            when {
                token.isNumeric() -> valueStack.push(token.toDouble())
                token.isOperator() -> {
                    while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())) {
                        performOperation(operatorStack, valueStack)
                    }
                    operatorStack.push(token)
                }
                token == "(" -> operatorStack.push(token)
                token == ")" -> {
                    while (!operatorStack.isEmpty() && operatorStack.peek() != "(") {
                        performOperation(operatorStack, valueStack)
                    }
                    if (!operatorStack.isEmpty() && operatorStack.peek() == "(") {
                        operatorStack.pop()
                    } else {
                        throw IllegalArgumentException("Invalid expression: Unbalanced parentheses")
                    }
                }
                else -> throw IllegalArgumentException("Invalid expression: Unknown token '$token'")
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek() == "(" || operatorStack.peek() == ")") {
                throw IllegalArgumentException("Invalid expression: Unbalanced parentheses")
            }
            performOperation(operatorStack, valueStack)
        }

        if (valueStack.size != 1) {
            throw IllegalArgumentException("Invalid expression")
        }

        return  valueStack.pop()
    }


}