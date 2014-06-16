package net.mokon.scrabble ;

public class StringWord extends Word {

  private String word ;

  public StringWord ( String word ) {
    super ( word ) ;
  }

  @Override public boolean equals ( Object obj ) {
    if ( this == obj ) { return true ; }
    if ( obj == null ) { return false ; }
    if ( this.getClass ( ) != obj.getClass ( ) ) { return false ; }
    final StringWord other = (StringWord) obj ;
    if ( this.word == null ) {
      if ( other.word != null ) { return false ; }
    } else if ( !this.word.equals ( other.word ) ) { return false ; }
    return true ;
  }

  public boolean equals ( String wS ) {
    int n = this.word.length ( ) ;
    if ( n == wS.length ( ) ) {
      char v1[] = this.word.toCharArray ( ) ;
      char v2[] = wS.toCharArray ( ) ;
      int i = 0 ;
      int j = 0 ;
      while ( n-- != 0 ) {
        if ( v1[ i ] != '*' && v2[ j ] != '*' && v1[ i++ ] != v2[ j++ ] ) { return false ; }
      }
      return true ;
    } else {
      return false ;
    }
  }

  @Override public boolean equals ( Word w ) {
    String wS = w.getWord ( ) ;
    int n = this.word.length ( ) ;
    if ( n == wS.length ( ) ) {
      char v1[] = this.word.toCharArray ( ) ;
      char v2[] = wS.toCharArray ( ) ;
      int i = 0 ;
      int j = 0 ;
      while ( n-- != 0 ) {
        if ( v1[ i ] != '*' && v2[ j ] != '*' && v1[ i++ ] != v2[ j++ ] ) { return false ; }
      }
      return true ;
    } else {
      return false ;
    }
  }

  @Override public char get ( int index ) {
    return this.word.charAt ( index ) ;
  }

  @Override public String getWord ( ) {
    return this.word ;
  }

  @Override public int hashCode ( ) {
    final int prime = 31 ;
    int result = 1 ;
    result = prime * result + ( this.word == null ? 0 : this.word.hashCode ( ) ) ;
    return result ;
  }

  @Override public int length ( ) {
    return this.word.length ( ) ;
  }

  @Override public void setWord ( String str ) {
    this.word = str ;

  }

}
