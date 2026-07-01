import 'package:equatable/equatable.dart';

abstract class QuoteEvent extends Equatable {
  const QuoteEvent();
  @override
  List<Object?> get props => [];
}

class LoadQuotes extends QuoteEvent {}

class SelectSymbol extends QuoteEvent {
  final String symbol;
  const SelectSymbol(this.symbol);
  @override
  List<Object?> get props => [symbol];
}

class RefreshQuote extends QuoteEvent {
  final String symbol;
  const RefreshQuote(this.symbol);
  @override
  List<Object?> get props => [symbol];
}
