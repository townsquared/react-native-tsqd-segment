// Copyright 2015-present 650 Industries. All rights reserved.

#import "TSQDSegment.h"

#import <SEGAnalytics.h>
#import <SEGAppsFlyerIntegrationFactory.h>

@interface TSQDSegment ()

@property (nonatomic, strong) SEGAnalytics *instance;

@end

@implementation TSQDSegment

RCT_EXPORT_MODULE(TSQDSegment);

RCT_EXPORT_METHOD(initialize:(NSString *)writeKey :(BOOL) includeAppsFlyer)
{
  SEGAnalyticsConfiguration *configuration = [SEGAnalyticsConfiguration configurationWithWriteKey:writeKey];
  if (includeAppsFlyer) {
    [configuration use:[SEGAppsFlyerIntegrationFactory instance]];
  }
  configuration.enableAdvertisingTracking = YES;
  configuration.trackApplicationLifecycleEvents = YES;
  configuration.trackDeepLinks = YES;
  configuration.trackPushNotifications = YES;
  configuration.trackAttributionData = YES;

  _instance = [[SEGAnalytics alloc] initWithConfiguration:configuration];
}

RCT_EXPORT_METHOD(identify:(NSString *)userId)
{
  if (_instance) {
    [_instance identify:userId];
  }
}


RCT_EXPORT_METHOD(identifyWithTraits:(NSString *)userId withTraits:(NSDictionary *)traits)
{
  if (_instance) {
    [_instance identify:userId traits:traits];
  }
}

RCT_EXPORT_METHOD(track:(NSString *)event)
{
  if (_instance) {
    [_instance track:event];
  }
}

RCT_EXPORT_METHOD(trackWithProperties:(NSString *)event withProperties:(NSDictionary *)properties)
{
  if (_instance) {
    [_instance track:event properties:properties];
  }
}

RCT_EXPORT_METHOD(group:(NSString *)groupId)
{
  if (_instance) {
    [_instance group:groupId];
  }
}

RCT_EXPORT_METHOD(groupWithTraits:(NSString *)groupId withTraits:(NSDictionary *)traits)
{
  if (_instance) {
    [_instance group:groupId traits:traits];
  }
}

RCT_EXPORT_METHOD(screen:(NSString *)screenName)
{
  if (_instance) {
    [_instance screen:screenName];
  }
}

RCT_EXPORT_METHOD(screenWithProperties:(NSString *)screenName withProperties:(NSDictionary *) properties)
{
  if (_instance) {
    [_instance screen:screenName properties:properties];
  }
}

RCT_EXPORT_METHOD(reset)
{
  if (_instance) {
    [_instance reset];
  }
}

RCT_EXPORT_METHOD(flush)
{
  if (_instance) {
    [_instance flush];
  }
}

@end
