//
// Created by Leo Dav on 24/05/22.
//

#import <Foundation/Foundation.h>

static NSString* MW_TEMPERATURE_METRIC_PREF = @"temperature_metric_pref";
static NSString* MW_THEME_PREF = @"theme_pref";

typedef NS_ENUM(NSInteger, MWTemperatureMetrics) {
    MWTemperatureMetricCelsius = 0,
    MWTemperatureMetricFahrenheit = 1,
    MWTemperatureMetricKelvin = 2
};

typedef NS_ENUM(NSInteger, MWThemeEnum) {
    MWThemeSystem = 0,
    MWThemeLight = 1,
    MWThemeDark = 2
};