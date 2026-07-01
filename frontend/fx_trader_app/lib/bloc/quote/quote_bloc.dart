import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:fx_trader_app/bloc/quote/quote_event.dart';
import 'package:fx_trader_app/bloc/quote/quote_state.dart';
import 'package:fx_trader_app/repositories/quote_repository.dart';

class QuoteBloc extends Bloc<QuoteEvent, QuoteState> {
  final QuoteRepository repository;

  QuoteBloc({required this.repository}) : super(QuoteInitial()) {
    on<LoadQuotes>(_onLoadQuotes);
    on<SelectSymbol>(_onSelectSymbol);
    on<RefreshQuote>(_onRefreshQuote);
  }

  Future<void> _onLoadQuotes(LoadQuotes event, Emitter<QuoteState> emit) async {
    emit(QuoteLoading());
    try {
      final symbols = await repository.getSymbols();
      emit(QuoteLoaded(symbols: symbols, selectedSymbol: symbols.isNotEmpty ? symbols.first : null));
    } catch (e) {
      emit(QuoteError(message: e.toString()));
    }
  }

  void _onSelectSymbol(SelectSymbol event, Emitter<QuoteState> emit) {
    final current = state;
    if (current is QuoteLoaded) {
      final selected = current.symbols.firstWhere((s) => s.symbol == event.symbol, orElse: () => current.symbols.first);
      emit(current.copyWith(selectedSymbol: selected));
    }
  }

  Future<void> _onRefreshQuote(RefreshQuote event, Emitter<QuoteState> emit) async {
    try {
      final quote = await repository.getSymbolQuote(event.symbol);
      final current = state;
      if (current is QuoteLoaded) {
        final updated = current.symbols.map((s) => s.symbol == event.symbol ? quote : s).toList();
        emit(current.copyWith(symbols: updated));
      }
    } catch (_) {}
  }
}
