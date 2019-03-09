using System;
using System.Collections.Generic;

namespace ChessGame.Services
{
    class ServiceProvider
    {
        private static Dictionary<Type, object> services = new Dictionary<Type, object>();

        public static T GetService<T>()
            where T : class
        {
            var serviceType = typeof(T);

            if (serviceType.Equals(typeof(AuthenticationService)))
            {
                if (!services.ContainsKey(serviceType))
                {
                    var service = new AuthenticationService(new Repositories.UserRepository());
                    services.Add(serviceType, service);
                }

                return (T)services[serviceType];
            }

            return null;
        }
    }
}