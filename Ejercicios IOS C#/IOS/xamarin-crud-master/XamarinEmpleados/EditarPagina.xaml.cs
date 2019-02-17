using System;
using System.Collections.Generic;

using Xamarin.Forms;

namespace XamarinEmpleados
{
    public partial class EditarPagina : ContentPage
    {
        private Empleado empleado;

        public EditarPagina(Empleado empleado)
        {
            InitializeComponent();
            this.empleado = empleado;
            ActualizarButton.Clicked += ActualizarButton_Clicked;
            BorrarButton.Clicked += BorrarButton_Clicked;
            nombresEntry.Text = empleado.Nombres;
            apellidosEntry.Text = empleado.Apellidos;
            salarioEntry.Text = empleado.Salario.ToString();
            fechaContratoDatePicker.Date = empleado.FechaContrato;
            activoSwitch.IsToggled = empleado.Activo;
        }

        public async void BorrarButton_Clicked(object sender, EventArgs e)
        {
			var rta = await DisplayAlert("Confirmacion", "Desea borrar el empleado?", "Si", "No");
			if (!rta) return;
            using (var datos = new AccesoAlosDatos())
			{
                datos.DeleteEmpleado(empleado);
			}
			await DisplayAlert("Confirmacion", "Empleado borrado correctamente", "Aceptar");
            await Navigation.PushAsync(new PaginadeInicio());
        }

		public async void ActualizarButton_Clicked(object sender, EventArgs e)
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
                idEmpleado = this.empleado.idEmpleado,
                Activo = activoSwitch.IsToggled,
                Apellidos = apellidosEntry.Text,
                Nombres = nombresEntry.Text,
                FechaContrato = fechaContratoDatePicker.Date,
                Salario = decimal.Parse(salarioEntry.Text)
			};
            using (var datos = new AccesoAlosDatos())
			{
                datos.UpdateEmpleado(empleado);
			}
			await DisplayAlert("Confirmacion", "Empleado actualizado correctamente", "Aceptar");
            await Navigation.PushAsync(new PaginadeInicio());
		}
    }
}
