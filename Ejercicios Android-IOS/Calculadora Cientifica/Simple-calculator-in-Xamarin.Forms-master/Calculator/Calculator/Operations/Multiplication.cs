namespace Calculator.Operations
{
    public class Multiplication : Operation
    {
        private string MULTIPLICATION_SYMBOL = "×";

        public override double action(double value01, double value02)
        {
            return value01 * value02;
        }

        public override bool verifyOperation(string operation)
        {
            return operation.Equals(MULTIPLICATION_SYMBOL);
        }
    }
}
