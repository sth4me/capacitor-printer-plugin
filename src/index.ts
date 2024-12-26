import { registerPlugin } from '@capacitor/core';

import type { PrinterPluginPlugin } from './definitions';

const PrinterPlugin = registerPlugin<PrinterPluginPlugin>('PrinterPlugin', {
  web: () => import('./web').then((m) => new m.PrinterPluginWeb()),
});

export * from './definitions';
export { PrinterPlugin };
