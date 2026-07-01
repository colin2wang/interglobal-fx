import 'package:equatable/equatable.dart';
import 'package:fx_trader_app/models/quote_model.dart';

abstract class QuoteState extends Equatable {
  const QuoteState();
  @override
  List<Object?> get props => [];
}

class QuoteInitial extends QuoteState {}
class QuoteLoading extends QuoteState {}

class QuoteLoaded extends QuoteState {
  final List<SymbolQuote> symbols;
  final SymbolQuote? selectedSymbol;
  const QuoteLoaded({required this.symbols, this.selectedSymbol});
  QuoteLoaded copyWith({List<SymbolQuote>? symbols, SymbolQuote? selectedSymbol}) =>
    QuoteLoaded(symbols: symbols ?? this.symbols, selectedSymbol: selectedSymbol ?? this.selectedSymbol);
  @override
  List<Object?> get props => [symbols, selectedSymbol];
}

class QuoteError extends QuoteState {
  final String message;
  const QuoteError({required this.message});
  @override
  List<Object?> get props => [message];
}
