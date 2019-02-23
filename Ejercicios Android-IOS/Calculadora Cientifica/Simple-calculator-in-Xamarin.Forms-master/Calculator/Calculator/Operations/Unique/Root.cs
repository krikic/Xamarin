using System;

namespace Calculator.Operations.Unique
{
    public class Root : OperationUnique
    {
        private string ROOT_SYMBOL = "√";

        public override double action(double value)
        {
            return Math.Sqrt(value);
        }

        public override bool verifyOperation(string operation)
        {
            return operation.Equals(ROOT_SYMBOL);
        }
    }
}
