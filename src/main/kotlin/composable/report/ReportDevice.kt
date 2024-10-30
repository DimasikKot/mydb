package composable.report

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import windows.ReportDeviceFromDB

@Composable
fun ReportDevice(
    reportDevice: ReportDeviceFromDB,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                Text(
                    "Код устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.deviceId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название устройства",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.deviceName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Код типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeId.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Название типа",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.typeName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Дата приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.date,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
            Spacer(
                Modifier.padding(top = 5.dp).fillMaxWidth().height(1.dp).border(1.dp, MaterialTheme.colors.background)
            )
            Row {
                Text(
                    "Цена приобретения",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                )
                Text(
                    reportDevice.price.toString(),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}