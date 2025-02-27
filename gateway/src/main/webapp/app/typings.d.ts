declare const VERSION: string;
declare const SERVER_API_URL: string;
declare const DEVELOPMENT: string;
declare const I18N_HASH: string;

declare module '*.json' {
  const value: any;
  export default value;
}

declare module '@crm/entities-routes' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@crm/entities-menu' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@notification/entities-routes' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@notification/entities-menu' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@attachment/entities-routes' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@attachment/entities-menu' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@agentai/entities-routes' {
  const _default: () => JSX.Element;
  export default _default;
}

declare module '@agentai/entities-menu' {
  const _default: () => JSX.Element;
  export default _default;
}
