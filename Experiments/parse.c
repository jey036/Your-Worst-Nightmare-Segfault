#include <stdio.h>
#include <time.h>

int main() {
  long num;

  while ( !feof(stdin) ) {
    scanf( "%ld\n", &num );
    printf( "%ld\n", num );
  }
    
  return 0;
}
