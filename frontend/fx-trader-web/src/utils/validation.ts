export const validators = {
  required: (value: any) => (value ? undefined : 'This field is required'),
  email: (value: string) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value) ? undefined : 'Invalid email',
  phone: (value: string) => /^1[3-9]\d{9}$/.test(value) ? undefined : 'Invalid phone',
  password: (value: string) => {
    if (value.length < 8) return 'Min 8 characters';
    if (!/[A-Z]/.test(value)) return 'Need uppercase';
    if (!/[a-z]/.test(value)) return 'Need lowercase';
    if (!/[0-9]/.test(value)) return 'Need number';
    return undefined;
  },
  number: (value: string) => /^\d+(\.\d+)?$/.test(value) ? undefined : 'Must be a valid number',
  positiveNumber: (value: number) => value > 0 ? undefined : 'Must be positive',
};

export const composeValidators = (...fns: Array<(v: any) => string | undefined>) => {
  return (value: any) => { for (const fn of fns) { const err = fn(value); if (err) return err; } return undefined; };
};
