using System;
using SQLite.Net.Interop;

namespace XamarinEmpleados
{
    public interface IConfig
    {
        string DirectorioDB { get; }
        ISQLitePlatform Plataforma { get;  }
    }
}
