package com.almohtaref.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.almohtaref.app.ui.theme.AlMohtarefTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AlMohtarefTheme { DashboardPrototype() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardPrototype() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("مركز المحترف", fontWeight = FontWeight.Bold)
                        Text("إدارة الصيانة والأجهزة المنزلية", style = MaterialTheme.typography.labelSmall)
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("أهلاً بيك", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("ملخص العمل — ستظهر المؤشرات بعد ربط قاعدة البيانات.")
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("طلبات جديدة", "—", Modifier.weight(1f))
                    MetricCard("قيد الإصلاح", "—", Modifier.weight(1f))
                }
            }
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MetricCard("جاهز للتسليم", "—", Modifier.weight(1f))
                    MetricCard("مواعيد اليوم", "—", Modifier.weight(1f))
                }
            }
            item {
                Text("الوصول السريع", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            item { QuickAction("إضافة عميل") }
            item { QuickAction("إنشاء طلب صيانة") }
            item { QuickAction("تسجيل دفعة") }
            item { QuickAction("إضافة مصروف") }
            item {
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.large) {
                    Column(Modifier.padding(16.dp)) {
                        Text("تنبيه التطوير", fontWeight = FontWeight.Bold)
                        Text("دي شاشة تأسيسية فقط. الأرقام والاختصارات غير موصولة بقاعدة بيانات حتى الآن، ولن تُعرض كوظائف مكتملة.")
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun QuickAction(label: String) {
    OutlinedButton(onClick = { /* Feature route will be connected in the next implementation phase. */ },
        modifier = Modifier.fillMaxWidth()) {
        Text(label)
    }
}
