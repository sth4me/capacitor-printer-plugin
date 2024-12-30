# capacitor-printer-plugin

Capacitor Printer Plugin For Android

## Install

```bash
npm install capacitor-printer-plugin
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`print(...)`](#print)
* [`getPrinterList()`](#getprinterlist)
* [`connectToPrinter(...)`](#connecttoprinter)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### print(...)

```typescript
print(options: PrintOptions) => Promise<PrintResponse>
```

| Param         | Type                                                  |
| ------------- | ----------------------------------------------------- |
| **`options`** | <code><a href="#printoptions">PrintOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#printresponse">PrintResponse</a>&gt;</code>

--------------------


### getPrinterList()

```typescript
getPrinterList() => Promise<{ printerList: USBPrinterInfo[]; }>
```

**Returns:** <code>Promise&lt;{ printerList: USBPrinterInfo[]; }&gt;</code>

--------------------


### connectToPrinter(...)

```typescript
connectToPrinter(options: { productId: number; }) => Promise<{ connected: boolean; }>
```

| Param         | Type                                |
| ------------- | ----------------------------------- |
| **`options`** | <code>{ productId: number; }</code> |

**Returns:** <code>Promise&lt;{ connected: boolean; }&gt;</code>

--------------------


### Interfaces


#### PrintResponse

| Prop             | Type                |
| ---------------- | ------------------- |
| **`message`**    | <code>string</code> |
| **`content`**    | <code>string</code> |
| **`debug_logs`** | <code>string</code> |


#### PrintOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`printObject`** | <code>string</code> |
| **`lineFeed`**    | <code>number</code> |


#### USBPrinterInfo

| Prop              | Type                 |
| ----------------- | -------------------- |
| **`productId`**   | <code>number</code>  |
| **`productName`** | <code>string</code>  |
| **`connected`**   | <code>boolean</code> |

</docgen-api>
