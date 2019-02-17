using System;
using SQLite.Net.Attributes;

namespace XamarinEmpleados
{
    public class Empleado
    {
        [PrimaryKey, AutoIncrement]
        public int idEmpleado { get; set;}
        public string Nombres { get; set;}
        public string Apellidos { get; set;}
        public DateTime FechaContrato { get; set;}
        public Decimal Salario { get; set; }
        public bool Activo { get; set;}

        public override string ToString()
        {
            return string.Format("{0}{1}{2}{3}{4}", idEmpleado, Nombres, Apellidos, FechaContrato, Salario, Activo);
        }
    }
}
