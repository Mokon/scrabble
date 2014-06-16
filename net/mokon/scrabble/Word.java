package net.mokon.scrabble ;

public abstract class Word {

  public Word ( String word ) {
    this.setWord ( word ) ;
  }

  @Override public abstract boolean equals ( Object obj ) ;

  public abstract boolean equals ( Word w ) ;

  public abstract char get ( int index ) ;

  public abstract String getWord ( ) ;

  @Override public abstract int hashCode ( ) ;

  public abstract int length ( ) ;

  public abstract void setWord ( String str ) ;

}
