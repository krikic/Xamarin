namespace Calculator.Operations
{
    public abstract class Operation
    {
        public abstract bool verifyOperation(string operation);
        public abstract double action(double value01, double value02);
    }
}
