//
// Created by Leo Dav on 17/05/22.
//

#import <Foundation/Foundation.h>
#import "MWForecast.h"

FOUNDATION_EXPORT NSString* MW_FAVOURITES_POI_ARRAY_KEY;

@interface MWFavouritesCache : NSObject

@property (strong, nonatomic) NSMutableDictionary<NSString*, MWForecast*>* favoritesCache;

+ (MWFavouritesCache*) reference;

- (void) saveFavourites;

- (void)addPoi:(MWPoi*)poi ThenExecuteSelector:(SEL)method OnObject:(id)object;

- (NSArray<MWForecast*>*)getAll;

- (BOOL)has: (NSString*) location;

- (MWForecast*) getByName: (NSString*) name;

- (NSUInteger)length;

- (void)add:(NSString*)location WithPrefetchedData:(MWForecast*)forecast;

- (void)remove:(NSString*)location;
@end