using Calculator.Operations;
using Xunit;

namespace TestCalculator
{
    public class OperationTest
    {
        [Theory]
        [InlineData(1, 1, 2)]
        [InlineData(-100, 3, -97)]
        [InlineData(-1, -3, -4)]
        [InlineData(2.02, 3.05, 5.07)]
        public void additionTest(double value01, double value02, double expected)
        {
            Addition adittion = new Addition();
            double additionResult = adittion.action(value01, value02);
            Assert.Equal(expected, additionResult);
        }

        [Theory]
        [InlineData(2, 1, 1)]
        [InlineData(-100, 5, -105)]
        [InlineData(-2, -3, 1)]
        [InlineData(3.1, 3.1, 0)]
        public void subtraction(double value01, double value02, double expected)
        {
            Subtraction subtraction = new Subtraction();
            double subtractionResult = subtraction.action(value01, value02);
            Assert.Equal(expected, subtractionResult);
        }

        [Theory]
        [InlineData(2, 2, 4)]
        [InlineData(-1, 5, -5)]
        [InlineData(-2, -3, 6)]
        [InlineData(3.20, 3.05, 9.76)]
        public void multiplication(double value01, double value02, double expected)
        {
            Multiplication multiplication = new Multiplication();
            double multiplicationResult = multiplication.action(value01, value02);
            Assert.Equal(expected, multiplicationResult);
        }

        [Theory]
        [InlineData(6, 2, 3)]
        [InlineData(-10, 5, -2)]
        [InlineData(-15, -5, 3)]
        [InlineData(4.50, 2.25, 2)]
        public void division(double value01, double value02, double expected)
        {
            Division division = new Division();
            double divisionResult = division.action(value01, value02);
            Assert.Equal(expected, divisionResult);
        }
    }
}
