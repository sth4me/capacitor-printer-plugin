import { registerPlugin } from '@capacitor/core';

import type { PrinterPlugin } from './definitions';

const Printer = registerPlugin<PrinterPlugin>('Printer', {
  web: () => import('./web').then((m) => new m.PrinterPluginWeb()),
});

export * from './definitions';
export { Printer };
