using System;
using System.Collections.Generic;
using System.Linq;
using SQLite.Net;
using Xamarin.Forms;
using System.IO;

namespace XamarinEmpleados
{
    class AccesoAlosDatos : IDisposable
    {
      private SQLiteConnection conexion;
        public AccesoAlosDatos()
        {
            var config = DependencyService.Get<IConfig>();
            conexion = new SQLiteConnection(config.Plataforma, Path.Combine(config.DirectorioDB, "Empleados.db3"));
            conexion.CreateTable<Empleado>();
        }

        public void InsertEmpleado(Empleado empleado)
        {
            conexion.Insert(empleado);
        }

		public void UpdateEmpleado(Empleado empleado)
		{
			conexion.Update(empleado);
		}

		public void DeleteEmpleado(Empleado empleado)
		{
			conexion.Delete(empleado);
		}

        public Empleado GetEmpleado(int IDEmpleado)
        {
            return conexion.Table<Empleado>().FirstOrDefault(conexion => conexion.idEmpleado == IDEmpleado);
        }

        public List<Empleado> GetEmpleado()
        {
            return conexion.Table<Empleado>().OrderBy(c => c.Apellidos).ToList();
        }

        public void Dispose()
        {
            conexion.Dispose();
        }
    }
}
