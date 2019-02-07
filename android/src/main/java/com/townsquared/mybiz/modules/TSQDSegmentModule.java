package com.townsquared.mybiz.modules;

import android.content.Context;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.segment.analytics.Analytics;
import com.segment.analytics.Options;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;
import com.segment.analytics.android.integrations.appsflyer.AppsflyerIntegration;


public class TSQDSegmentModule extends ReactContextBaseJavaModule {

  private static final String TAG = TSQDSegmentModule.class.getSimpleName();
  private Analytics mClient = null;
  private Boolean includeAppsFlyer = false;
  private String writeKey;

  public TSQDSegmentModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  private Properties toProperties (ReadableMap map) {
    if (map == null) {
      return new Properties();
    }
    Properties props = new Properties();

    ReadableMapKeySetIterator iterator = map.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType type = map.getType(key);
      switch (type){
        case Array:
          props.putValue(key, map.getArray(key));
          break;
        case Boolean:
          props.putValue(key, map.getBoolean(key));
          break;
        case Map:
          props.putValue(key, map.getMap(key));
          break;
        case Null:
          props.putValue(key, null);
          break;
        case Number:
          props.putValue(key, map.getDouble(key));
          break;
        case String:
          props.putValue(key, map.getString(key));
          break;
        default:
          break;
      }
    }
    return props;
  }

  private Traits toTraits (ReadableMap map) {
    if (map == null) {
      return new Traits();
    }
    Traits traits = new Traits();

    ReadableMapKeySetIterator iterator = map.keySetIterator();
    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      ReadableType type = map.getType(key);
      switch (type){
        case Array:
          traits.putValue(key, map.getArray(key));
          break;
        case Boolean:
          traits.putValue(key, map.getBoolean(key));
          break;
        case Map:
          traits.putValue(key, map.getMap(key));
          break;
        case Null:
          traits.putValue(key, null);
          break;
        case Number:
          traits.putValue(key, map.getDouble(key));
          break;
        case String:
          traits.putValue(key, map.getString(key));
          break;
        default:
          break;
      }
    }
    return traits;
  }

  private Analytics getClient() {
      if (mClient == null) {
          try {
              // this is the equivalent of a getSingleton, but fails when it hasn't been created
              mClient = Analytics.with(null);
          } catch (IllegalArgumentException ex) {
              Context context = getReactApplicationContext().getApplicationContext();
              Analytics.Builder builder = new Analytics.Builder(context, this.writeKey);
              if (this.includeAppsFlyer) {
                builder.use(AppsflyerIntegration.FACTORY);
              }
              mClient = builder
                      .trackApplicationLifecycleEvents()
                      .trackAttributionInformation()
                      .build();
              Analytics.setSingletonInstance(mClient);
          }
      }

      return mClient;
  }

  @Override
  public String getName() {
    return "TSQDSegment";
  }

  @ReactMethod
  public void initialize(final String writeKey, final Boolean includeAppsFlyer) {
      this.writeKey = writeKey;
      this.includeAppsFlyer = includeAppsFlyer;
      getClient();
  }

  @ReactMethod
  public void identify(final String userId) {
    Analytics client = getClient();
    if (client != null) {
      client.identify(userId);
    }
  }

  @ReactMethod
  public void identifyWithTraits(final String userId, final ReadableMap properties) {
    Analytics client = getClient();
    if (client != null) {
      client.identify(userId, toTraits(properties), new Options());
    }
  }

  @ReactMethod
  public void track(final String eventName) {
    Analytics client = getClient();
    if (client != null) {
      client.track(eventName);
    }
  }

  @ReactMethod
  public void trackWithProperties(final String eventName, final ReadableMap properties) {
    Analytics client = getClient();
    if (client != null) {
      client.track(eventName, toProperties(properties));
    }
  }

  @ReactMethod
  public void group(final String groupId) {
    Analytics client = getClient();
    if (client != null) {
      client.group(groupId);
    }
  }

  @ReactMethod
  public void groupWithTraits(final String groupId, final ReadableMap properties) {
    Analytics client = getClient();
    if (client != null) {
      client.group(groupId, toTraits(properties), new Options());
    }
  }

  @ReactMethod
  public void screen(final String screenName) {
    Analytics client = getClient();
    if (client != null) {
      client.screen(screenName);
    }
  }

  @ReactMethod
  public void screenWithProperties(final String screenName, final ReadableMap properties) {
    Analytics client = getClient();
    if (client != null) {
      client.screen(screenName, toProperties(properties));
    }
  }

  @ReactMethod
  public void flush() {
    Analytics client = getClient();
    if (client != null) {
      client.flush();
    }
  }

  @ReactMethod
  public void reset() {
    Analytics client = getClient();
    if (client != null) {
      client.reset();
    }
  }
}
