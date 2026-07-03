const TOKEN_KEY = 'fx_trader_token';

export const getToken = (): string | null => localStorage.getItem(TOKEN_KEY);
export const setToken = (token: string): void => {
  localStorage.setItem(TOKEN_KEY, token);
};
export const removeToken = (): void => {
  localStorage.removeItem(TOKEN_KEY);
};

export const getLocale = (): string => localStorage.getItem('fx_locale') || 'zh-CN';
export const setLocale = (locale: string): void => {
  localStorage.setItem('fx_locale', locale);
};
export const clearStorage = (): void => localStorage.clear();
