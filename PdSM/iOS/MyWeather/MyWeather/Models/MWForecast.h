//
// Created by Leo Dav on 15/05/22.
//

#import <Foundation/Foundation.h>
#import "MWPoi.h"
#import "MWWeatherData.h"

@interface MWForecast : NSObject

@property (nonatomic, weak) MWPoi* location;

@property (nonatomic, strong) MWWeatherData* current;
@property (nonatomic, strong) NSArray<MWWeatherData*>* hourly;
@property (nonatomic, strong) NSArray<MWWeatherData*>* daily;

-(instancetype) initWithJSONData:(NSData*) data AndPoi:(MWPoi*)poi;

-(void) refreshCurrentWeather;

@end

typedef void (^MWForecastConsumer)(MWForecast* forecast);
