namespace Calculator.Operations.Unique
{
    public class Percentage : OperationUnique
    {
        private string PERCENTAGE_SYMBOL = "%";

        public override double action(double value)
        {
            return value / 100;
        }

        public override bool verifyOperation(string operation)
        {
            return operation.Equals(PERCENTAGE_SYMBOL);
        }
    }
}
