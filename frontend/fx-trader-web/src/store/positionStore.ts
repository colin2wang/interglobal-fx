import { create } from 'zustand';
import type { Position } from '@/api/position';

interface PositionState {
  positions: Position[];
  totalProfit: number;
  totalMargin: number;
  setPositions: (positions: Position[]) => void;
  updatePosition: (position: Position) => void;
  removePosition: (positionId: string) => void;
}

const calcTotals = (positions: Position[]) => ({
  totalProfit: positions.reduce((sum, p) => sum + p.profit, 0),
  totalMargin: positions.reduce((sum, p) => sum + p.margin, 0),
});

export const usePositionStore = create<PositionState>((set) => ({
  positions: [],
  totalProfit: 0,
  totalMargin: 0,
  setPositions: (positions) => set({ positions, ...calcTotals(positions) }),
  updatePosition: (position) => set((state) => {
    const positions = state.positions.map((p) => p.id === position.id ? position : p);
    return { positions, ...calcTotals(positions) };
  }),
  removePosition: (positionId) => set((state) => {
    const positions = state.positions.filter((p) => p.id !== positionId);
    return { positions, ...calcTotals(positions) };
  }),
}));
