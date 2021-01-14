fun main(args: Array<String>) {
    val calculator = ExpressionCalculator()
    val example: Long = calculator.evaluateExpression(args)
    print("Answer: $example")
}