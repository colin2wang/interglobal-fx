import { create } from 'zustand';
import type { AccountInfo } from '@/api/account';

interface AccountState {
  accountInfo: AccountInfo | null;
  setAccountInfo: (info: AccountInfo) => void;
}

export const useAccountStore = create<AccountState>((set) => ({
  accountInfo: null,
  setAccountInfo: (info) => set({ accountInfo: info }),
}));
