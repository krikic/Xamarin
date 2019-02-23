
using Calculator;
using Xunit;

namespace TestCalculator
{
    public class MainAppTest
    {
        MainApp mainApp = new MainApp();

        [Theory]
        [InlineData("%", "2", "0.02")]
        [InlineData("√", "9", "3")]
        [InlineData("x²", "5", "25")]
        public void selectOperatorUniqueTest(string buttonText, string resultText, string expected)
        {
            string result = mainApp.selectOperatorUnique(buttonText, resultText);
            Assert.Equal(expected, result);
        }

        [Theory]
        [InlineData(1, 2, "+", "3")]
        [InlineData(6, 2, "-", "4")]
        [InlineData(5, 2, "×", "10")]
        [InlineData(4, 2, "÷", "2")]
        public void calculateTest(double firstNumber,double secondNumber, string selectedOperator, string expected)
        {
            mainApp.clear();
            mainApp.setFirstNumber(firstNumber);
            mainApp.setSecondNumber(secondNumber);
            mainApp.setSelectedOperator(selectedOperator);
            string result = mainApp.calculate();
            Assert.Equal(expected, result);
        }

        [Theory]
        [InlineData("2","1", MainApp.State.FirstNumber, "12")]
        [InlineData("3", "4", MainApp.State.SecondNumber, "43")]
        [InlineData("8", "10", MainApp.State.SelectedOperator, "8")]
        public void selectNumberAlterResultTextTest(string buttonText, string resultText, MainApp.State state, string expected)
        {
            mainApp.setCurrentState(state);
            string result = mainApp.selectNumberAlterResultText(buttonText, resultText);
            Assert.Equal(expected, result);
        }
    }
}
