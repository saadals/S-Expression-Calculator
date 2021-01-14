import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ExpressionCalculatorTest {
    private val calculator = ExpressionCalculator()

    @Test
    fun empty_arguments() {
        assertThrows<Exception> {
            calculator.evaluateExpression(emptyArray())
        }
    }

    @Test
    fun unbalanced_brackets() {
        assertThrows<Exception> {
            calculator.evaluateExpression("(add 123 456))")
        }
    }

    @Test
    fun single_digit_only() {
        assertEquals(calculator.evaluateExpression("456"), 456L)
    }

    @Test
    fun complex_expression() {
        assertEquals(calculator.evaluateExpression("(multiply 3 (multiply (multiply 3 3) (add 3 5)))"), 216L)
    }

    @Test
    fun complex_expression_with_division() {
        assertEquals(
            calculator.evaluateExpression("(divide (add (multiply 9 6) 99) (multiply (multiply 3 3) (add 3 5)))"), 2L)
    }

    @Test
    fun expression_with_negative_numbers() {
        assertEquals(
            calculator.evaluateExpression("(divide (subtract (multiply 61 6) 14) (multiply -5 (add 3 5)))"), -9L)
    }
}