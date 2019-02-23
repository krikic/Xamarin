using Calculator.Operations.Unique;
using Xunit;

namespace TestCalculator
{
    public class OperationUniqueTest
    {
        [Theory]
        [InlineData(1, 0.01)]
        [InlineData(100, 1)]
        [InlineData(-1, -0.01)]
        [InlineData(50, 0.5)]
        public void percentage(double value, double expected)
        {
            Percentage percentage = new Percentage();
            double percentageResult = percentage.action(value);
            Assert.Equal(expected, percentageResult);
        }

        [Theory]
        [InlineData(9, 3)]
        [InlineData(49, 7)]
        [InlineData(1, 1)]
        [InlineData(1.5625, 1.25)]
        public void root(double value, double expected)
        {
            Root root = new Root();
            double rootResult = root.action(value);
            Assert.Equal(expected, rootResult);
        }

        [Theory]
        [InlineData(2, 4)]
        [InlineData(6, 36)]
        [InlineData(-5, 25)]
        [InlineData(2.1, 4.41)]
        public void square(double value, double expected)

        {
            Square square = new Square();
            double squareResult = square.action(value);
            Assert.Equal(expected, squareResult);
        }
    }
}
