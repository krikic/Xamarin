using System;
using Xamarin.Forms;

namespace XamarinEmpleados
{
    class EmpleadoCell: ViewCell
    {
        public EmpleadoCell()
        {
			var idEmployeeLabel = new Label
			{
				FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)),
				HorizontalOptions = LayoutOptions.End
			};
			idEmployeeLabel.SetBinding(Label.TextProperty, new Binding("idEmpleado"));

			var nombresLabel = new Label
			{
				FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)),
			};
			nombresLabel.SetBinding(Label.TextProperty, new Binding("Nombres"));

			var apellidosLabel = new Label
			{
				FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)),
				HorizontalOptions = LayoutOptions.Fill
			};
			apellidosLabel.SetBinding(Label.TextProperty, new Binding("Apellidos"));

			var salarioLabel = new Label
			{
				FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)),
				HorizontalOptions = LayoutOptions.Fill
			};
			salarioLabel.SetBinding(Label.TextProperty, new Binding("Salario"));

			var fechaContratoLabel = new Label
			{
				FontSize = Device.GetNamedSize(NamedSize.Medium, typeof(Label)),
				HorizontalOptions = LayoutOptions.Fill
			};
			fechaContratoLabel.SetBinding(Label.TextProperty, new Binding("FechaContrato"));

			var activoSwitch = new Switch
			{
				HorizontalOptions = LayoutOptions.End
			};
			activoSwitch.SetBinding(Switch.IsToggledProperty, new Binding("Activo"));

			var panel1 = new StackLayout
			{
				Children = { idEmployeeLabel, nombresLabel, apellidosLabel },
				Orientation = StackOrientation.Horizontal
			};

			var panel2 = new StackLayout
			{
				Children = { salarioLabel, fechaContratoLabel, activoSwitch },
				Orientation = StackOrientation.Horizontal
			};

			View = new StackLayout
			{
				Children = { panel1, panel2 },
				Orientation = StackOrientation.Horizontal
			};
		}
    }
}
