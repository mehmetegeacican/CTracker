export function groupBy(data, groupKey, sumKeys) {
    return data.reduce((result, item) => {
      // Use reportDate as the grouping key
      const groupValue = item[groupKey];
      
      if (!result[groupValue]) {
        // Initialize the grouped entry with the first occurrence
        result[groupValue] = {
          [groupKey]: groupValue,
          ...sumKeys.reduce((acc, key) => ({ ...acc, [key]: 0 }), {}),
        };
      }
      
      // Sum up the values for each sumKey
      sumKeys.forEach((key) => {
        result[groupValue][key] += item[key];
      });
      
      return result;
    }, {});
  }
  