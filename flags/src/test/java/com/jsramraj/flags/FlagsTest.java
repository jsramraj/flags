package com.jsramraj.flags;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.MockitoJUnitRunner;

import com.jsramraj.flags.Flags.FlagsException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidParameterException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlagsTest {

    @Mock
    Context mContext;
    @Mock
    Resources mResources;
    @Mock
    AssetManager mAssetManager;

    @Before
    public void setUp() {
        when(mResources.getAssets()).thenReturn(mAssetManager);
        when(mContext.getResources()).thenReturn(mResources);

        URL resource = FlagsTest.class.getClassLoader().getResource("all_flags.png");
        try {
            InputStream inputStream = new FileInputStream(resource.getPath());
            doReturn(inputStream).when(mAssetManager).open(anyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = FlagsException.class)
    public void verifyIfFlags_ThrowException_IfContextIsNotSet() {
        Flags.forCountry("US");
    }

    @Test(expected = NullPointerException.class)
    public void verifyIfFlags_ThrowException_WhenNullValueIsPassed_AsCountryCode() {
        Flags.init(mContext);
        Flags.forCountry(null);
    }

    @Test(expected = InvalidParameterException.class)
    public void verifyIfFlags_ThrowException_WhenWrongNumberOfCharacterArePassed_AsCountryCode() {
        Flags.init(mContext);
        Flags.forCountry("U");
    }

    @Test(expected = InvalidParameterException.class)
    public void verifyIfFlags_ThrowException_WhenWrongNumberOfCharacterArePassed_AsCountryCode1() {
        Flags.init(mContext);
        Flags.forCountry("ABC");
    }

    @Test
    public void verifyIfFlags_WorksFine_ForRightParameter() {
        Flags.init(mContext);
        Flags.forCountry("US");
    }


    @After
    public void tearDown() {
        Flags.init(null);
    }
}
