package com.okason.prontoshop.core.dagger;


import com.okason.prontoshop.core.MainActivity;
import com.okason.prontoshop.fragments.CheckoutFragment;
import com.okason.prontoshop.fragments.CustomerListFragment;
import com.okason.prontoshop.fragments.ProductListFragment;
import com.okason.prontoshop.fragments.TransactionListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Valentine on 4/18/2016.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                ShoppingCartModule.class,
                BusModule.class
        }
)
public interface AppComponent {
        void inject(ProductListFragment fragment);
        void inject(CustomerListFragment fragment);
        void inject(CheckoutFragment fragment);
        void inject(TransactionListFragment fragment);
        void inject(MainActivity activity);
}
