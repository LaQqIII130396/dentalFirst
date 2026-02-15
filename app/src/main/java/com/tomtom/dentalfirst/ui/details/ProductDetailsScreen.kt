package com.tomtom.dentalfirst.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomtom.dentalfirst.R
import com.tomtom.dentalfirst.data.model.Product
import com.tomtom.dentalfirst.viewmodel.ProductDetailsViewModel
import com.tomtom.dentalfirst.viewmodel.ProductDetailsViewModelFactory

@Composable
fun ProductDetailsScreen(
    productId: String, onBackClick: () -> Unit
) {
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ProductDetailsViewModelFactory(productId)
    )
    val product = viewModel.product.collectAsState()

    if (product.value != null) {
        ProductDetailsScreen(product.value!!, onBackClick)
    } else {
        ApologizeScreen(onBackClick)
    }
}

@Composable
private fun ProductDetailsScreen(product: Product, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6)),
            contentPadding = PaddingValues(16.dp)
        ) {

            item { ProductImage(product.imageRes) }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                CategoryChips(
                    categories = listOf(
                        stringResource(R.string.product_details_category_dental_materials),
                        stringResource(R.string.product_details_category_processing_filling),
                        stringResource(R.string.product_details_category_oral_cavity)
                    )
                )
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item { LabelsRow() }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item { Title(product.name) }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item { ExpirationBlock() }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item { CompareBlock() }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item { AnalogButton() }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { CharacteristicsBlock() }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            item { TabsBlock(product) }
        }
        TopBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding(),
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun ApologizeScreen(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.product_details_apologize_screen),
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF6B6B6B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBackClick,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(stringResource(R.string.product_details_apologize_screen_back_button))
        }
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier, onBackClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.Black
            )
        }

        Row {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Share, null)
            }

            IconButton(onClick = {}) {
                Icon(Icons.Default.FavoriteBorder, null)
            }
        }
    }
}

@Composable
private fun ProductImage(imageRes: Int) {
    Card(
        shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(246.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun CategoryChips(
    categories: List<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->

            FilterChip(selected = false, onClick = {}, label = {
                Text(
                    text = category, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            })
        }
    }
}

@Composable
private fun LabelsRow() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .background(Color(0xFF19B661), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(stringResource(R.string.product_details_label_new), color = Color.White)
        }

        Box(
            modifier = Modifier
                .background(Color(0xFFFF8A00), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(stringResource(R.string.product_details_label_discount_25), color = Color.White)
        }
    }
}

@Composable
private fun Title(text: String) {
    Text(
        text = text, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
    )
}

@Composable
private fun ExpirationBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFF4EA), shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(R.string.product_details_expiration_label),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B6B6B)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.product_details_expiration_12_months),
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFFF8A00),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CompareBlock() {

    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(
                color = Color(0xFFF2F2F2), shape = RoundedCornerShape(16.dp)
            )
            .clickable { checked = !checked }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Text(
            text = stringResource(R.string.product_details_compare), color = Color(0xFF4A4A4A)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = checked, onCheckedChange = { checked = it })
    }
}

@Composable
private fun AnalogButton() {
    val blue = Color(0xFF2F80ED)
    val backgroundBlue = blue.copy(alpha = 0.12f)

    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundBlue, contentColor = blue
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        )
    ) {
        Text(
            text = stringResource(R.string.product_details_show_analogs),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CharacteristicsBlock() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.product_details_characteristics_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        CharacteristicItem(
            title = stringResource(R.string.product_details_sku_title),
            value = stringResource(R.string.product_details_sku_value),
            showCopyIcon = true
        )

        CharacteristicItem(
            title = stringResource(R.string.product_details_id_title),
            value = stringResource(R.string.product_details_id_value),
            showCopyIcon = true
        )

        CharacteristicItem(
            title = stringResource(R.string.product_details_manufacturer_title),
            value = stringResource(R.string.product_details_manufacturer_value),
            isLink = true
        )

        CharacteristicItem(
            title = stringResource(R.string.product_details_country_title),
            value = stringResource(R.string.product_details_country_value),
            isLink = true
        )
    }
}

@Composable
private fun CharacteristicItem(
    title: String, value: String, showCopyIcon: Boolean = false, isLink: Boolean = false
) {

    val linkColor = Color(0xFF2F80ED)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title, style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B6B6B)
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isLink) linkColor else Color.Black,
                fontWeight = if (isLink) FontWeight.Medium else FontWeight.Normal
            )

            if (showCopyIcon) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
            }

            if (isLink) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = linkColor
                )
            }
        }
    }
}

@Composable
private fun TabsBlock(product: Product) {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        stringResource(R.string.product_details_tab_description),
        stringResource(R.string.product_details_tab_bought_with)
    )

    Column {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            itemsIndexed(tabs) { index, title ->

                val isSelected = selectedTab == index
                val blue = Color(0xFF2F80ED)

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) blue.copy(alpha = 0.12f)
                    else Color(0xFFF1F1F1),
                    modifier = Modifier.clickable { selectedTab = index }) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = if (isSelected) blue else Color(0xFF6B6B6B),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (isSelected) FontWeight.Medium
                        else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {

            0 -> Text(
                text = product.description
                    ?: stringResource(R.string.product_details_tab_description_content),
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp,
                color = Color(0xFF333333)
            )

            1 -> Text(
                text = stringResource(R.string.product_details_tab_bought_with_content),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

