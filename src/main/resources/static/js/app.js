const API_BASE = 'http://localhost:8093/api';

let allVacancies = [];
let allCompanies = [];
let allCategories = [];

// İnit
document.addEventListener('DOMContentLoaded', () => {
    loadVacancies();
    loadCompanies();
    loadCategories();
    loadIndustries();
    populateFilters();
});

// Tab Dəyişdirmə
function switchTab(tabName, element) {
    // Bütün tab-ları gizlə
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));
    
    // Bütün düymələri deaktiv et
    const buttons = document.querySelectorAll('.tab-button');
    buttons.forEach(btn => btn.classList.remove('active'));
    
    // Seçilən tab-ı göstər
    document.getElementById(tabName + '-tab').classList.add('active');
    
    // Seçilən düyməni aktiv et
    element.classList.add('active');
}

// Vakansiyaları Yüklə
async function loadVacancies() {
    try {
        const response = await fetch(`${API_BASE}/vacancies`);
        allVacancies = await response.json();
        displayVacancies(allVacancies);
    } catch (error) {
        console.error('Xəta:', error);
        document.getElementById('vacanciesList').innerHTML = '<p class="error">Vakansiyalar yükləmə uğursuz oldu</p>';
    }
}

// Şirkətləri Yüklə
async function loadCompanies() {
    try {
        const response = await fetch(`${API_BASE}/companies`);
        allCompanies = await response.json();
        displayCompanies(allCompanies);
    } catch (error) {
        console.error('Xəta:', error);
        document.getElementById('companiesList').innerHTML = '<p class="error">Şirkətlər yükləmə uğursuz oldu</p>';
    }
}

// Kateqoriyaları Yüklə
async function loadCategories() {
    try {
        const response = await fetch(`${API_BASE}/categories`);
        allCategories = await response.json();
        displayCategories(allCategories);
    } catch (error) {
        console.error('Xəta:', error);
        document.getElementById('categoriesList').innerHTML = '<p class="error">Kateqoriyalar yükləmə uğursuz oldu</p>';
    }
}

// Sənayəni Yüklə
async function loadIndustries() {
    try {
        const response = await fetch(`${API_BASE}/industries`);
        allIndustries = await response.json();
        displayIndustries(allIndustries);
    } catch (error) {
        console.error('Xəta:', error);
        document.getElementById('industriesList').innerHTML = '<p class="error">Sənaye yükləmə uğursuz oldu</p>';
    }
}

// Vakansiyaları Göstər
function displayVacancies(vacancies) {
    const container = document.getElementById('vacanciesList');
    
    if (vacancies.length === 0) {
        container.innerHTML = '<tr><td colspan="4" class="no-results">Nəticə tapılmadı</td></tr>';
        return;
    }

    container.innerHTML = vacancies.map(vacancy => {
        const publishedDate = new Date(vacancy.publishedDate).toLocaleDateString('az-AZ');
        const deadline = vacancy.deadline ? new Date(vacancy.deadline).toLocaleDateString('az-AZ') : '-';
        return `
            <tr onclick="showVacancyDetail(${vacancy.id})">
                <td><span class="badge employment">${formatEmploymentType(vacancy.employmentType)}</span></td>
                <td><span class="badge">${vacancy.categoryName || '-'}</span></td>
                <td>${publishedDate}</td>
                <td>${deadline}</td>
            </tr>
        `;
    }).join('');
}



// Şirkətləri Göstər
function displayCompanies(companies) {
    const container = document.getElementById('companiesList');
    
    if (companies.length === 0) {
        container.innerHTML = '<tr><td colspan="2" class="no-results">Nəticə tapılmadı</td></tr>';
        return;
    }

    container.innerHTML = companies.map(company => {
        const vacancyCount = allVacancies.filter(v => v.companyId === company.id).length;
        return `
            <tr>
                <td>${company.name}</td>
                <td>${vacancyCount}</td>
            </tr>
        `;
    }).join('');
}

// Kateqoriyaları Göstər
function displayCategories(categories) {
    const container = document.getElementById('categoriesList');
    
    if (categories.length === 0) {
        container.innerHTML = '<tr><td colspan="2" class="no-results">Nəticə tapılmadı</td></tr>';
        return;
    }

    container.innerHTML = categories.map(category => `
        <tr onclick="showCategoryVacancies(${category.id}, '${category.name}')">
            <td>${category.name}</td>
        </tr>
    `).join('');
}

// Kateqoriyaya Aid Vakansiyaları Göstər
let currentCategoryVacancies = [];

function showCategoryVacancies(categoryId, categoryName) {
    currentCategoryVacancies = allVacancies.filter(v => v.categoriesId == categoryId);
    
    document.getElementById('categoryVacanciesTitle').textContent = `"${categoryName}" - ${currentCategoryVacancies.length} Vakansiya`;
    document.getElementById('sortBySalary').value = 'default';
    displayCategoryVacancies(currentCategoryVacancies);
    
    document.getElementById('categoryVacanciesSection').style.display = 'block';
    document.getElementById('categoryVacanciesSection').scrollIntoView({ behavior: 'smooth' });
}

// Kateqoriya Vakansiyalarını Göstər
function displayCategoryVacancies(vacancies) {
    document.getElementById('categoryVacanciesList').innerHTML = vacancies.length > 0 
        ? vacancies.map(vacancy => `
            <div class="card vacancy-card" onclick="showVacancyDetail(${vacancy.id})">
                <h3>${vacancy.title}</h3>
                <p class="company-name">🏢 ${vacancy.companyName}</p>
                <p>${vacancy.description?.substring(0, 100)}...</p>
                <div class="card-meta">
                    <span class="badge employment">${formatEmploymentType(vacancy.employmentType)}</span>
                    <span class="badge location">📍 ${formatLocation(vacancy.location)}</span>
                </div>
                ${vacancy.salaryM ? `<p class="salary">💰 ${vacancy.salaryM.toLocaleString('az-AZ')} ₼</p>` : ''}
            </div>
        `).join('')
        : '<p class="no-results">Bu kateqoriyada vakansiya yoxdur</p>';
}

// Maaş Enum Sırası ve Etiketleri
const salaryOrder = {
    'BELOW_500': 1,
    'FROM_500_TO_1000': 2,
    'FROM_1000_TO_2000': 3,
    'ABOVE_2000': 4
};

const salaryLabels = {
    'BELOW_500': '500 AZN altı',
    'FROM_500_TO_1000': '500 - 1000 AZN',
    'FROM_1000_TO_2000': '1000 - 2000 AZN',
    'ABOVE_2000': '2000 AZN üstü'
};



// Sənayəni Göstər
function displayIndustries(industries) {
    const container = document.getElementById('industriesList');
    
    if (industries.length === 0) {
        container.innerHTML = '<tr><td colspan="2" class="no-results">Nəticə tapılmadı</td></tr>';
        return;
    }

    container.innerHTML = industries.map(industry => `
        <tr>
            <td>${industry.name}</td>
        </tr>
    `).join('');
}

// Vakansiya Detalını Göstər
async function showVacancyDetail(vacancyId) {
    try {
        const response = await fetch(`${API_BASE}/vacancies/${vacancyId}`);
        const vacancy = await response.json();
        
        const modal = document.getElementById('vacancyModal');
        const modalBody = document.getElementById('modalBody');
        
        const contactEmail = vacancy.contactEmail || 'Müraciət emaili mövcud deyil';
        
        modalBody.innerHTML = `
            <h2>${vacancy.title}</h2>
            <div class="detail-row">
                <strong>Şirkət:</strong>
                <span>${vacancy.companyName}</span>
            </div>
            <div class="detail-row">
                <strong>Kateqoriya:</strong>
                <span>${vacancy.categoryName}</span>
            </div>
            <div class="detail-row">
                <strong>İstiqdam Növü:</strong>
                <span>${formatEmploymentType(vacancy.employmentType)}</span>
            </div>
            <div class="detail-row">
                <strong>Məkan:</strong>
                <span>${formatLocation(vacancy.location)}</span>
            </div>
            <div class="detail-row">
                <strong>Vəzifə Səviyyəsi:</strong>
                <span>${formatDegreeOfDuty(vacancy.degreeOfDuty)}</span>
            </div>
            ${vacancy.salary ? `
            <div class="detail-row">
                <strong>Maaş:</strong>
                <span>${salaryLabels[vacancy.salary] || vacancy.salary}</span>
            </div>
            ` : ''}
            <h3 style="margin-top: 1.5rem; margin-bottom: 1rem;">Təsvir</h3>
            <p>${vacancy.description}</p>
            <div class="detail-row" style="margin-top: 1.5rem; padding: 1rem; background-color: #f5f5f5; border-radius: 4px;">
                <strong>📧 Müraciət Emaili:</strong>
                <span style="color: #0066cc; font-weight: bold;">${contactEmail}</span>
            </div>
            <button onclick="applyForVacancy(${vacancyId}, '${contactEmail}')" class="apply-button" style="margin-top: 1.5rem; width: 100%; padding: 0.75rem; font-size: 1rem; cursor: pointer;">
                Müraciət Et
            </button>
        `;
        
        modal.style.display = 'block';
    } catch (error) {
        console.error('Xəta:', error);
        alert('Vakansiya detalları yükləmə uğursuz oldu');
    }
}

// Vakansiyaya Müraciət Et
function applyForVacancy(vacancyId, contactEmail) {
    alert(`Müraciət emaili: ${contactEmail}\n\nBu emailə müraciət etmək üçün zəhmət olmasa CV-nizi göndərin.`);
}

// Modali Bağla
function closeModal() {
    document.getElementById('vacancyModal').style.display = 'none';
}

// Filtrələri Doldur
async function populateFilters() {
    try {
        // Kateqoriyalar
        const categorySelect = document.getElementById('categoryFilter');
        allCategories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            categorySelect.appendChild(option);
        });

        // Şirkətlər
        const companySelect = document.getElementById('companyFilter');
        allCompanies.forEach(company => {
            const option = document.createElement('option');
            option.value = company.id;
            option.textContent = company.name;
            companySelect.appendChild(option);
        });
    } catch (error) {
        console.error('Filtrələr yükləmə uğursuz oldu:', error);
    }
}

// Vakansiyaları Axtar
function searchVacancies() {
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();
    
    if (!searchTerm) {
        displayVacancies(allVacancies);
        return;
    }

    const filtered = allVacancies.filter(vacancy =>
        vacancy.title.toLowerCase().includes(searchTerm) ||
        (vacancy.description && vacancy.description.toLowerCase().includes(searchTerm)) ||
        (vacancy.companyName && vacancy.companyName.toLowerCase().includes(searchTerm)) ||
        (vacancy.categoryName && vacancy.categoryName.toLowerCase().includes(searchTerm))
    );

    displayVacancies(filtered);
}

// Şirkətləri Axtar
function searchCompanies() {
    const searchTerm = document.getElementById('companiesSearchInput').value.toLowerCase();
    
    if (!searchTerm) {
        displayCompanies(allCompanies);
        return;
    }

    const filtered = allCompanies.filter(company =>
        company.name.toLowerCase().includes(searchTerm)
    );

    displayCompanies(filtered);
}

// Kateqoriyaları Axtar
function searchCategories() {
    const searchTerm = document.getElementById('categoriesSearchInput').value.toLowerCase();
    
    if (!searchTerm) {
        displayCategories(allCategories);
        return;
    }

    const filtered = allCategories.filter(category =>
        category.name.toLowerCase().includes(searchTerm)
    );

    displayCategories(filtered);
}

// Sənayəni Axtar
function searchIndustries() {
    const searchTerm = document.getElementById('industriesSearchInput').value.toLowerCase();
    
    if (!searchTerm) {
        displayIndustries(allIndustries);
        return;
    }

    const filtered = allIndustries.filter(industry =>
        industry.name.toLowerCase().includes(searchTerm)
    );

    displayIndustries(filtered);
}





// Format Funksiyaları
function formatEmploymentType(type) {
    const types = {
        'FULL_TIME': 'Tam Vaxtlı',
        'PART_TIME': 'Yarım Vaxtlı',
        'REMOTE': 'Uzaqdan',
        'FREELANCE': 'Azad Fəaliyyət',
        'DISTANCE': 'Məsafəli'
    };
    return types[type] || type;
}

function formatLocation(location) {
    const locations = {
        'BAKU': 'Bakı',
        'GANDJA': 'Gəncə',
        'SUMGAYIT': 'Sumqayıt',
        'NAXCIVAN': 'Naxçıvan',
        'LANKARAN': 'Lənkəran',
        'MINGACHEVIR': 'Mingəçevir',
        'SHAMAKHI': 'Şamaxı',
        'SHIRVAN': 'Şirvan',
        'SHEKI': 'Şəki',
        'QUBA': 'Quba',
        'QABALA': 'Qəbələ',
        'SAKI': 'Saki',
        'ASTARA': 'Astara',
        'FIZULI': 'Fizuli',
        'AGDAM': 'Ağdam',
        'AGSU': 'Ağsu',
        'ISMAILI': 'İsmayıllı',
        'BALAKAN': 'Balakən',
        'ZAQATALA': 'Zaqatala',
        'TOVUZ': 'Tovuz',
        'QAZAX': 'Qazax',
        'LACHIN': 'Laçın',
        'KUBATLI': 'Qubadlı',
        'JEBRAYIL': 'Cəbrayıl',
        'HADRUT': 'Hadrut'
    };
    return locations[location] || location;
}

function formatDegreeOfDuty(degree) {
    const degrees = {
        'INTERNSHIP': 'Praktikant',
        'ENTRY': 'Başlanğıc',
        'ASSOCIATE': 'Mütəxəssis',
        'MID_SENIOR': 'Orta/Baş Mütəxəssis',
        'DIRECTOR': 'Direktor',
        'EXECUTIVE': 'İcraçı'
    };
    return degrees[degree] || degree;
}

// Xarici Kliklə Modali Bağla
window.onclick = function(event) {
    const modal = document.getElementById('vacancyModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

// Enter Düyməsi Axtarış üçün
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                searchVacancies();
            }
        });
    }

    // Companies search
    const companiesSearchInput = document.getElementById('companiesSearchInput');
    if (companiesSearchInput) {
        companiesSearchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                searchCompanies();
            }
        });
    }

    // Categories search
    const categoriesSearchInput = document.getElementById('categoriesSearchInput');
    if (categoriesSearchInput) {
        categoriesSearchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                searchCategories();
            }
        });
    }

    // Industries search
    const industriesSearchInput = document.getElementById('industriesSearchInput');
    if (industriesSearchInput) {
        industriesSearchInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                searchIndustries();
            }
        });
    }
});

// Əlaqə Widget Açıl/Bağla
function toggleContactWidget() {
    const popup = document.getElementById('contactPopup');
    const aboutPopup = document.getElementById('aboutPopup');
    aboutPopup.classList.remove('active');
    popup.classList.toggle('active');
}

// Haqqımızda Widget Açıl/Bağla
function toggleAboutWidget() {
    const popup = document.getElementById('aboutPopup');
    const contactPopup = document.getElementById('contactPopup');
    contactPopup.classList.remove('active');
    popup.classList.toggle('active');
}



// Bütün Sənayə Məlumatını Saxla
let allIndustries = [];
