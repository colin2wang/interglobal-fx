declare module 'nprogress' {
  const NProgress: {
    start: () => void;
    done: () => void;
    configure: (options: { showSpinner?: boolean }) => void;
  };
  export default NProgress;
}
