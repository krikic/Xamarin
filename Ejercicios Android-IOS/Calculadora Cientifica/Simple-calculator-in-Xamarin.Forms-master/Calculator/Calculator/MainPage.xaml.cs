using System;
using Xamarin.Forms;

namespace Calculator
{
    public partial class MainPage : ContentPage
    {
        MainApp mainApp = new MainApp();

        public MainPage()
        {
            InitializeComponent();
            OnClear(this, null);
        }

        private void OnSelectNumber(object sender, EventArgs e)
        {
            Button button = (Button)sender;
            resultText.Text =  mainApp.selectNumberAlterResultText(button.Text, resultText.Text);
            mainApp.setNumber(resultText.Text);
        }

        private void OnSelectOperatorUnique(object sender, EventArgs e)
        {
            Button button = (Button)sender;
            resultText.Text = mainApp.selectOperatorUnique(button.Text, resultText.Text);
        }

        private void OnSelectOperator(object sender, EventArgs e)
        {
            Button button = (Button)sender;
            mainApp.selectOperator(button.Text);
        }

        public void OnCalculate(object sender, EventArgs e)
        {
            resultText.Text = mainApp.calculate();
        }

        private void OnClear(object sender, EventArgs e)
        {
            resultText.Text = mainApp.clear();
        }
    }
}
