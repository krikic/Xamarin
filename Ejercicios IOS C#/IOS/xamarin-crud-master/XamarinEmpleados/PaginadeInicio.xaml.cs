using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace XamarinEmpleados
{
    public partial class PaginadeInicio : ContentPage
    {
        public PaginadeInicio()
        {
            InitializeComponent();
            AgregarButton.Clicked += AgregarButton_Clicked;
            listaListView.ItemSelected += ListaListView_ItemSelected;

            listaListView.ItemTemplate = new DataTemplate(typeof(EmpleadoCell));

            using (var datos = new AccesoAlosDatos())
            {
                listaListView.ItemsSource = datos.GetEmpleado();
            }
        }

        private void ListaListView_ItemSelected(object sender, SelectedItemChangedEventArgs e)
        {
            Navigation.PushAsync(new EditarPagina((Empleado)e.SelectedItem));
        }

        public async void AgregarButton_Clicked(object sender, EventArgs e)
        {
			if (string.IsNullOrEmpty(nombresEntry.Text))
			{
				await DisplayAlert("Error", "Debe ingresar nombres", "Aceptar");
				nombresEntry.Focus();
				return;
			}

			if (string.IsNullOrEmpty(apellidosEntry.Text))
			{
				await DisplayAlert("Error", "Debe ingresar apellidos", "Aceptar");
				apellidosEntry.Focus();
				return;
			}

			if (string.IsNullOrEmpty(salarioEntry.Text))
			{
				await DisplayAlert("Error", "Debe ingresar salario", "Aceptar");
				salarioEntry.Focus();
				return;
			}

            Empleado empleado = new Empleado
            {
                Activo = activoSwitch.IsToggled,
                Apellidos = apellidosEntry.Text,
                FechaContrato = fechaContratoDatePicker.Date,
                Nombres = nombresEntry.Text,
                Salario = decimal.Parse(salarioEntry.Text)
            };

            using (var datos = new AccesoAlosDatos())
            {
                datos.InsertEmpleado(empleado);
                listaListView.ItemsSource = datos.GetEmpleado();
            }

			apellidosEntry.Text = string.Empty;
			fechaContratoDatePicker.Date = DateTime.Now;
			nombresEntry.Text = string.Empty;
			salarioEntry.Text = string.Empty;
			await DisplayAlert("Mensaje", "Empleado creado correctamente", "Aceptar");
        }
    }
}
