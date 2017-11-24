package com.andy.dribbble;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lixn on 2017/11/7.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class MainActivityTest {
    @Test
    public void testTitle() {
        Activity act = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(act);
    }
}
