using Calculator.Operations;
using Calculator.Operations.Unique;
using System;
using System.Collections.Generic;

namespace Calculator
{
    public class MainApp
    {
        public enum State { FirstNumber, SecondNumber, SelectedOperator };

        private List<Operation> operations = new List<Operation>();
        private List<OperationUnique> operationsUnique = new List<OperationUnique>();

        private State currentState = State.FirstNumber;
        private string selectedOperator;
        private double firstNumber, secondNumber;

        public MainApp()
        {
            addOperation();
            addOperationUnique();
        }

        public string selectOperatorUnique(string buttonText, string resultText)
        {
            double result = 0;

            foreach (OperationUnique operationUnique in operationsUnique)
            {
                if (operationUnique.verifyOperation(buttonText))
                {
                    result = operationUnique.action(Convert.ToDouble(resultText));
                    break;
                }
            }
            return getResultTextOperation(result);
        }

        public void selectOperator(string buttonText)
        {
            selectedOperator = buttonText;
            currentState = State.SelectedOperator;
        }

        public string calculate()
        {
            double result = 0;

            foreach (Operation operation in operations)
            {
                if (operation.verifyOperation(selectedOperator))
                {
                    result = operation.action(firstNumber, secondNumber);
                    break;
                }
            }
            return getResultTextOperation(result);
        }

        public string clear()
        {
            firstNumber = 0;
            secondNumber = 0;
            currentState = State.FirstNumber;
            return "";
        }

        public string selectNumberAlterResultText(string buttonText, string resultText)
        {
            if (currentState.Equals(State.SelectedOperator))
            {
                resultText = "";
                currentState = State.SecondNumber;
            }
            resultText += buttonText;
            return resultText;
        }

        public void setNumber(string resultText)
        {
            if (currentState.Equals(State.FirstNumber))
                firstNumber = Convert.ToDouble(resultText);
            else
                secondNumber = Convert.ToDouble(resultText);
        }

        private string getResultTextOperation(double result)
        {
            firstNumber = result;
            currentState = State.FirstNumber;
            return result.ToString();
        }

        private void addOperation()
        {
            Addition addition = new Addition();
            Subtraction subtraction = new Subtraction();
            Multiplication multiplication = new Multiplication();
            Division division = new Division();

            operations.Add(addition);
            operations.Add(subtraction);
            operations.Add(multiplication);
            operations.Add(division);
        }

        private void addOperationUnique()
        {
            Percentage percentage = new Percentage();
            Root root = new Root();
            Square square = new Square();

            operationsUnique.Add(percentage);
            operationsUnique.Add(root);
            operationsUnique.Add(square);
        }

        public void setSelectedOperator(string selectedOperator)
        {
            this.selectedOperator = selectedOperator;
        }

        public void setFirstNumber(double firstNumber)
        {
            this.firstNumber = firstNumber;
        }

        public void setSecondNumber(double secondNumber)
        {
            this.secondNumber = secondNumber;
        }

        public void setCurrentState(State state)
        {
            currentState = state;
        }
    }
}
