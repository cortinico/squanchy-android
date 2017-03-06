package net.squanchy.schedule;

import android.content.Context;

import net.squanchy.navigation.Navigator;
import net.squanchy.service.firebase.FirebaseDbService;
import net.squanchy.service.firebase.injection.DbServiceType;
import net.squanchy.support.lang.Checksum;

import dagger.Module;
import dagger.Provides;

@Module
class ScheduleModule {

    private final Context context;

    ScheduleModule(Context context) {
        this.context = context;
    }

    @Provides
    ScheduleService scheduleService(@DbServiceType(DbServiceType.Type.AUTHENTICATED) FirebaseDbService dbService, Checksum checksum) {
        return new ScheduleService(dbService, checksum);
    }

    @Provides
    Context context() {
        return context;
    }

    @Provides
    Navigator scheduleNavigator(Context context) {
        return new ScheduleNavigator(context);
    }
}
