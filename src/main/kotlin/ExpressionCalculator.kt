import org.apache.commons.lang3.StringUtils
import kotlin.math.roundToLong

class ExpressionCalculator {

    private val ADD = "ADD"
    private val MULTIPLY = "MULTIPLY"
    private val SUBTRACT = "SUBTRACT"
    private val DIVIDE = "DIVIDE"

    private fun grammarCheck(args: Array<String>) {
        if (args.isEmpty())
            throw Exception("No argument was provided")

        if (args.size > 1)
            throw Exception("Quotation marks needs to be added around the whole statement if the statement is more than one number")

        if (StringUtils.countMatches(args[0], "(") != StringUtils.countMatches(args[0], ")"))
            throw Exception("Every open bracket needs to correspond to a close bracket and vice versa")
    }

    private fun getFirstOperand(expression: String): String {

        //Means this particular operand is just a digit
        if (expression[0] != '(') return expression.substring(0, expression.indexOf(" "))

        val operand = StringBuilder()
        var netBrackets = 0

        for (i in expression.indices) {
            if (expression[i] == '(') netBrackets++
            else if (expression[i] == ')') netBrackets--
            else if (netBrackets == 0) return operand.toString()
            operand.append(expression[i])
        }
        return operand.toString()
    }

    private fun performOperation(operation: String, firstOperand: Long, secondOperand: Long): Long {
        return when (operation.toUpperCase()) {
            ADD -> firstOperand + secondOperand
            SUBTRACT -> firstOperand - secondOperand
            MULTIPLY -> firstOperand * secondOperand
            DIVIDE -> {
                println("NOTE: Divsion operations are rounded to the nearest integer")
                (firstOperand.toFloat() / secondOperand.toFloat()).roundToLong()
            }
            else -> throw Exception("$operation is not a supported operation")
        }
    }

    fun evaluateExpression(expression: String): Long {
        if (expression.matches("-?[0-9]+".toRegex())) {           //Statement checks if expression is numbers only
            return expression.toLong()
        }

        val subExpressions: List<String> = expression.substring(1, expression.length - 1).split(" ".toRegex(), 2)
        val operator: String = subExpressions[0]
        val operands: String = subExpressions[1]
        val firstOperator: String = getFirstOperand(operands)
        val secondOperator: String = operands.substring(firstOperator.length + 1)

        return performOperation(operator, evaluateExpression(firstOperator), evaluateExpression(secondOperator))
    }

    fun evaluateExpression(expression: Array<String>): Long {
        grammarCheck(expression)
        return evaluateExpression(expression[0])
    }

}