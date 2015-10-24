#import <stdio.h>
#import <time.h>

int main() {
  char character;

  while ( !feof(stdin) ) {
    scanf( "%c", &character );
    switch ( character ) {
      case '0' : break;
      case '1' : break;
      case '2' : break;
      case '3' : break;
      case '4' : break;
      case '5' : break;
      case '6' : break;
      case '7' : break;
      case '8' : break;
      case '9' : break;
      case ',': 
        printf( "%c", '\n' );
        continue;
      default  : continue;
    }
    printf("%c", character );
  }

  return 0;
}
