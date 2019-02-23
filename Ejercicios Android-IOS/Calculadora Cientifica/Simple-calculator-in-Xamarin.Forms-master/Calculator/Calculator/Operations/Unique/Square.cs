namespace Calculator.Operations.Unique
{
    public class Square : OperationUnique
    {
        private string SQUARE_SYMBOL = "x²";

        public override double action(double value)
        {
            return value * value;
        }

        public override bool verifyOperation(string operation)
        {
            return operation.Equals(SQUARE_SYMBOL);
        }
    }
}
